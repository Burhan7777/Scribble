import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun exportAndSharePdfCheckBox(
    context: Context,
    title: String,                    // Title of the checkbox note
    checkboxStates: ArrayList<Boolean>, // List of checkbox states (true/false)
    checkboxTexts: List<String>        // List of corresponding texts
) {
    // Create a PdfDocument instance
    val pdfDocument = PdfDocument()

    // Define page size (A4: 595x842)
    val pageWidth = 595
    val pageHeight = 842
    val pageContentTop = 100f  // Starting y position for content
    val pageContentBottom = 800f  // Bottom margin

    // Variables to track current page
    var yPos = pageContentTop

    // Create the first page
    var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    var page = pdfDocument.startPage(pageInfo)
    var canvas: Canvas = page.canvas

    // Set up paint for text and checkboxes
    val paint = Paint().apply {
        textSize = 16f
        color = android.graphics.Color.BLACK
    }

    // Set up paint for checkbox borders (unchecked boxes)
    val boxPaint = Paint().apply {
        style = Paint.Style.STROKE // Only draw the outline for unchecked boxes
        strokeWidth = 3f
    }

    // Set up paint for checkmarks
    val checkPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = android.graphics.Color.BLACK
    }

    // Define the margin and maximum line width
    val pageMargin = 50f
    val maxTextWidth = 500f // Width within the margins

    // Draw the title at the top of the first page
    paint.textSize = 24f // Larger text size for the title
    canvas.drawText(title, pageMargin, yPos, paint)

    // Reset paint text size for checkboxes and associated text
    paint.textSize = 16f

    yPos += 50f // Move the position down after the title

    for (i in checkboxTexts.indices) {
        // Draw checkbox outline (for both checked and unchecked states)
        val left = pageMargin
        val top = yPos - 20f
        val right = left + 20f
        val bottom = yPos
        canvas.drawRect(left, top, right, bottom, boxPaint)

        // Draw a checkmark if the checkbox is checked
        if (checkboxStates[i]) {
            // Draw checkmark (an X) from corner to corner
            // Line 1: Top-left to bottom-right
            canvas.drawLine(left, top, right, bottom, checkPaint)
            // Line 2: Bottom-left to top-right
            canvas.drawLine(left, bottom, right, top, checkPaint)
        }

        // Handle long text by splitting it into lines
        val text = checkboxTexts[i]
        val lines = getWrappedLines(text, paint, maxTextWidth) // Get the wrapped lines of text

        // Draw each line of the text, wrapping if necessary
        for (line in lines) {
            // Check if drawing the next line exceeds the bottom margin, if so, create a new page
            if (yPos + paint.textSize + 10f > pageContentBottom) {
                // Finish the current page
                pdfDocument.finishPage(page)

                // Start a new page
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pdfDocument.pages.size + 1).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas

                // Reset yPos for the new page
                yPos = pageContentTop

                // Draw the title again on top of the new page (optional)
                paint.textSize = 24f
                canvas.drawText(title, pageMargin, yPos, paint)
                paint.textSize = 16f

                yPos += 50f // Add space below the title for the content
            }

            // Draw the text line
            canvas.drawText(line, left + 50f, yPos, paint)
            yPos += paint.textSize + 10f // Move down for the next line
        }

        // Add extra space between checkboxes
        yPos += 20f
    }

    // Finish the last page
    pdfDocument.finishPage(page)

    // Save the document in the cache directory
    val filePath = File(context.cacheDir, "$title.pdf")
    val fileOutputStream = FileOutputStream(filePath)
    pdfDocument.writeTo(fileOutputStream)

    // Close the document
    pdfDocument.close()

    // Share the PDF file
    sharePdf(context, filePath)
}

private fun getWrappedLines(text: String, paint: Paint, maxWidth: Float): List<String> {
    val lines = mutableListOf<String>()
    var currentLine = StringBuilder()

    // Break the text into words
    val words = text.split(" ")

    for (word in words) {
        // Check if adding the next word would exceed the max width
        val potentialLine = if (currentLine.isEmpty()) word else currentLine.toString() + " " + word
        val textWidth = paint.measureText(potentialLine)

        if (textWidth > maxWidth) {
            // If the line exceeds the max width, start a new line
            lines.add(currentLine.toString())
            currentLine = StringBuilder(word)
        } else {
            // Otherwise, add the word to the current line
            if (currentLine.isNotEmpty()) {
                currentLine.append(" ")
            }
            currentLine.append(word)
        }
    }

    // Add the last line
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine.toString())
    }

    return lines
}

private fun sharePdf(context: Context, file: File) {
    // Get the URI for the file using FileProvider
    val fileUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",  // Ensure the provider is correctly declared in Manifest
        file
    )

    // Create a share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, fileUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permission to the receiving app
    }

    // Launch the share intent
    context.startActivity(Intent.createChooser(shareIntent, "Share PDF using"))
}
