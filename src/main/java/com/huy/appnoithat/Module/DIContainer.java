package com.huy.appnoithat.Module;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

public final class DIContainer {
    private static Injector injector;

    @SafeVarargs
    public static <T> T get(T... reified) {
        if (injector == null) {
            throw new RuntimeException("No injector created");
        }
        return injector.getInstance(getClassOf(reified));
    }

    public static <T> T get(Class<T> type) {
        if (injector == null) {
            throw new RuntimeException("No injector created");
        }
        return injector.getInstance(type);
    }

    private static <T> Class<T> getClassOf(T[] array) {
        Class<?> componentType = array.getClass().getComponentType();
        if (componentType == null) {
            throw new IllegalStateException("Component type cannot be determined");
        }
        // Check if the componentType can be cast to Class<T>
        if (!Object.class.isAssignableFrom(componentType)) {
            throw new ClassCastException("Cannot cast component type to Class<T>");
        }
        @SuppressWarnings("unchecked")
        Class<T> result = (Class<T>) componentType;
        return result;
    }

    public static void createInjector() {
        if (injector != null) {
            throw new RuntimeException("Injector can only be created one time");
        }
        injector = Guice.createInjector(Stage.PRODUCTION, new AppNoiThatModule());
    }
}