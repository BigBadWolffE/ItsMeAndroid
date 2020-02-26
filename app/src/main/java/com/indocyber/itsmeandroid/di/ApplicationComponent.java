package com.indocyber.itsmeandroid.di;

import android.app.Application;

import com.indocyber.itsmeandroid.repositories.SecretQuestionRepository;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.dao.SecretQuestionDao;
import com.indocyber.itsmeandroid.services.database.dao.UserDao;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.services.network.ApiService;
import com.indocyber.itsmeandroid.view.App;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
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
