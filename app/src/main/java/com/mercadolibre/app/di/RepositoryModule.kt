package com.mercadolibre.app.di

import com.mercadolibre.app.network.ApiService
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: ApiService,
        //recipeMapper: RecipeDtoMapper,
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeService = recipeService,
            //mapper = recipeMapper
        )
    }
}