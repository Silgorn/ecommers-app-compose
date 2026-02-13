package com.grizzlyfungames.ecommersappcompose.di

import com.grizzlyfungames.ecommersappcompose.data.repository.CategoryRepositoryImpl
import com.grizzlyfungames.ecommersappcompose.data.repository.ProductRepositoryImpl
import com.grizzlyfungames.ecommersappcompose.domain.repository.CategoryRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}