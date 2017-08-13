const settings = {
    release: {
        mode: process.env.RELEASE_TYPE || "staging"
    },
    env: {
        ENV_PRODUCTION: "production",
        ENV_STAGING: "staging",
        ENV_TEST: "test"
    },
    port: process.env.PORT || 8080
}

module.exports = settings;
