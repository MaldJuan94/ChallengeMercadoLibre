package com.mercadolibre.app.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker.Companion.getOrCreate
import com.mercadolibre.app.ui.navigarion.Navigation
import com.mercadolibre.app.ui.utils.DevicePosture
import com.mercadolibre.app.ui.utils.isBookPosture
import com.mercadolibre.app.ui.utils.isSeparatingPosture
import com.mercadolibre.app.ui.utils.isTableTopPosture
import com.mercadolibre.app.ui.viewmodel.RootViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val viewModel: RootViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        val devicePosture = getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
                when {
                    isTableTopPosture(foldingFeature) ->
                        DevicePosture.TableTopPosture(foldingFeature.bounds)
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)
                    isSeparatingPosture(foldingFeature) ->
                        DevicePosture.SeparatingPosture(
                            foldingFeature.bounds,
                            foldingFeature.orientation
                        )
                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
        setContent {
            BaseApp {
                Navigation(
                    viewModel = viewModel,
                    devicePosture = devicePosture
                )
            }
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        viewModel.getData()
        return super.onCreateView(parent, name, context, attrs)
    }
}
