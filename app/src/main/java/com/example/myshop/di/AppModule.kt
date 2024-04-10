package com.example.myshop.di

import android.app.Application
import androidx.room.Room
import com.example.myshop.data.data_source.ApplicationDatabase
//import com.example.myshop.data.repository.ItemRepositoryImpl
import com.example.myshop.data.repository.BagRepositoryImpl
import com.example.myshop.data.repository.ItemRepositoryImpl
//import com.example.myshop.domain.repository.ItemRepository
import com.example.myshop.domain.repository.BagRepository
import com.example.myshop.domain.repository.ItemRepository
//import com.example.myshop.domain.use_case.items.AddItem
//import com.example.myshop.domain.use_case.items.DeleteItem
//import com.example.myshop.domain.use_case.items.GetItem
//import com.example.myshop.domain.use_case.items.GetItemsShop
//import com.example.myshop.domain.use_case.items.ItemUseCases
import com.example.myshop.domain.use_case.bag.AddBag
import com.example.myshop.domain.use_case.bag.DeleteBag
import com.example.myshop.domain.use_case.bag.GetBag
import com.example.myshop.domain.use_case.bag.GetBags
import com.example.myshop.domain.use_case.bag.BagUseCases
import com.example.myshop.domain.use_case.items.AddItem
import com.example.myshop.domain.use_case.items.DeleteItem
import com.example.myshop.domain.use_case.items.GetItem
import com.example.myshop.domain.use_case.items.GetItemsBag
import com.example.myshop.domain.use_case.items.ItemUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ApplicationDatabase{
        return Room.databaseBuilder(
            app,
            ApplicationDatabase::class.java,
            ApplicationDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemRepository(db: ApplicationDatabase): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
    }

    @Provides
    @Singleton
    fun provideShopItemRepository(db: ApplicationDatabase): BagRepository{
        return BagRepositoryImpl(db.bagDao)
    }

    @Provides
    @Singleton
    fun provideItemUseCases(repository: ItemRepository): ItemUseCases {
        return ItemUseCases(
            getItemsBag = GetItemsBag(repository),
            deleteItem = DeleteItem(repository),
            addItem = AddItem(repository),
            getItem = GetItem(repository)
        )
    }

    @Provides
    @Singleton
    fun provideShopItemUseCases(repository: BagRepository): BagUseCases{
        return BagUseCases(
            getBag = GetBag(repository),
            getBags = GetBags(repository),
            addBag = AddBag(repository),
            deleteBag = DeleteBag(repository)
        )
    }

}