package com.mercadolibre.app.ui.utils

class ComposePager<Key, Model>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (loading: Boolean, query: String) -> Unit,
    private inline val onRequest: suspend (nextKey: Key, query: String, type: String) -> Result<List<Model>>,
    private inline val onGetNextKey: suspend (List<Model>) -> Key,
    private inline val onError: suspend (cause: Throwable?) -> Unit,
    private inline val onSuccess: suspend (data: List<Model>, newKey: Key, query: String, type: String) -> Unit
) : Pager<Key, Model> {

    private var currentKey = initialKey
    private var loading = false

    override suspend fun onLoadNextData(query: String, type: String) {
        if (loading) return
        loading = true
        onLoadUpdated(true, type)
        val result = onRequest(currentKey, query, type)
        val data = result.getOrElse {
            onError(it)
            onLoadUpdated(false, type)
            return
        }
        currentKey = onGetNextKey(data)
        onSuccess(data, currentKey, query, type)
        loading = false
        onLoadUpdated(false, type)
    }

    override fun onReset() {
        currentKey = initialKey
    }
}