package de.toadette.poc.rtbm.presentation;

import android.app.Application;

import dagger.ObjectGraph;

public class RtbmApplication extends Application {
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(new RtbmModule());
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
