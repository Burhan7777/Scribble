package com.pzbdownloaders.scribble.search_main_screen_feature.domain.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.search_main_screen_feature.utils.ClientSearch

class GetArchiveSearchResultUseCase {

    suspend fun getArchiveSearchResult(searchQuery: String): ArrayList<AddNote> {
//        val client = ClientSearch.getClientSearch()
//        val index = client.initIndex(IndexName("content1"))
//
//        val query = query {
//            query = searchQuery
//            hitsPerPage = 50
//            attributesToRetrieve {
//                +"title"
//                +"content"
//                +"timeStamp"
//                +"color"
//                +"label"
//                +"archived"
//                +"noteId"
//            }
//            filters {
//                and {
//                    facet("userId", FirebaseAuth.getInstance().currentUser?.uid!!)
//                    facet("archived", true)
//                }
//            }
//        }
//
//        val result = index.search(query)
//        val obj = result.hits.deserialize(AddNote.serializer())
//        Log.i("searchId", obj.toString())
//        return obj.toCollection(ArrayList())
//    }
        return emptyList<AddNote>().toCollection(ArrayList())
    }
}