package de.toadette.poc.rtbm.presentation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.toadette.poc.rtbm.domain.model.vip.VipRepository;
import de.toadette.poc.rtbm.port.adapter.FakeVipRepository;

@Module(injects = {
        StartActivity.class
})
public class RtbmModule {


    @Provides
    @Singleton
    VipRepository provideVipRepository() {
        return new FakeVipRepository();
    }
}
