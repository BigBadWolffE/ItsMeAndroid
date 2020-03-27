package com.indocyber.itsmeandroid.di;

import android.app.Application;

import com.indocyber.itsmeandroid.viewremastered.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Singleton
@Component(modules = {ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

//    ApiService apiService();
//    Api api();
//    ImageCardDao imageCardDao();
//    UserDao userDao();
//    SecretQuestionDao secretQuestionDao();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }

    void inject(App app);
}
