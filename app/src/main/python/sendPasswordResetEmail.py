import smtplib
import secrets

def send_reset_code(userEmail: str):
    reset_code = secrets.token_urlsafe(4)

    email = "pzbappsemailreset@gmail.com"
    connection = smtplib.SMTP("smtp.gmail.com", 587)
    connection.starttls()
    connection.login(user=email, password="wewedmsmflybkrgp")
    connection.sendmail(from_addr=email, to_addrs=userEmail,
                        msg=f"Subject:Password reset\n\nThis is your reset code:{reset_code}")
    connection.close()
    return reset_code


