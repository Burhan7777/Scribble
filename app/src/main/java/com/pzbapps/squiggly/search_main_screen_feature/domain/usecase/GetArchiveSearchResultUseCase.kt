package com.pzbapps.squiggly.search_main_screen_feature.domain.usecase

import com.pzbapps.squiggly.add_note_feature.domain.model.AddNote

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