package main;

import manager.DefaultManager;

public enum Managers {
    DEFAULT_MANAGER {
        @Override
        ThingManager getInstance() {
            return new DefaultManager();
        }
    },
    OTHER_MANAGER {
        @Override
        ThingManager getInstance() {
            return null;
        }
    };

    abstract ThingManager getInstance();
}
