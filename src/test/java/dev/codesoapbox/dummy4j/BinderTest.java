package dev.codesoapbox.dummy4j;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class BinderTest {

    @Test
    void test()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Binder binder = new Binder(new Dummy4j());
        OperatingSystem result = binder.bind("operating_system.win", OperatingSystem.class);
        System.out.println(result);
    }

    private static class OperatingSystem {

        private String name;
        private String version;

        public OperatingSystem() {
        }

        public OperatingSystem(String name, String version) {
            this.name = name;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "OperatingSystem{" +
                    "name='" + name + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }
}