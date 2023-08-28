package com.pzbdownloaders.scribble.search_feature.utils

import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID

object ClientSearch {

    fun getClientSearch():ClientSearch {
        return ClientSearch(
            applicationID = ApplicationID("49CFHQZBRY"),
            apiKey = APIKey("619d55ef714c903efe836ed5d6ead2b4")
        )
    }
}