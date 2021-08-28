package tk.skeptick.skyeng_dictionary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.skeptick.skyeng_dictionary.domain.DictionaryRepository
import tk.skeptick.skyeng_dictionary.repositories.DictionaryRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideDictionaryRepository(implementation: DictionaryRepositoryImpl): DictionaryRepository

}