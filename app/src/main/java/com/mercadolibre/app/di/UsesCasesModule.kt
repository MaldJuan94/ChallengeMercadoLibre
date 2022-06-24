package com.mercadolibre.app.di

import android.content.Context
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.usescases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsesCasesModule {

    @Provides
    @Singleton
    fun provideGetSearchProductUseCase(
        repository: RecipeRepository,
        @ApplicationContext context: Context
    ) = GetSearchProductUseCase(
        repository,
        context
    )

    @Provides
    @Singleton
    fun provideGetSearchProductByCategoryUseCase(
        repository: RecipeRepository,
        @ApplicationContext context: Context
    ) = GetSearchProductByCategoryUseCase(
        repository,
        context
    )

    @Provides
    @Singleton
    fun provideGetProductUseCase(
        repository: RecipeRepository,
        @ApplicationContext context: Context
    ) = GetProductUseCase(
        repository,
        context
    )

    @Provides
    @Singleton
    fun provideGetProductDescriptionUseCase(
        repository: RecipeRepository,
        @ApplicationContext context: Context
    ) = GetProductDescriptionUseCase(
        repository,
        context
    )

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        repository: RecipeRepository,
        @ApplicationContext context: Context
    ) = GetCategoriesUseCase(
        repository,
        context
    )

}