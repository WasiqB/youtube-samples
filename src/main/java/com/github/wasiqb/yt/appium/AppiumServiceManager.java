package com.github.wasiqb.yt.appium;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOCAL_TIMEZONE;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.USE_DRIVERS;
import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.nio.file.Path;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Builder;

@Builder (builderMethodName = "composeService", buildMethodName = "composed")
public class AppiumServiceManager {
    private static final String USER_DIR = getProperty ("user.dir");

    @Builder.Default
    private String driverName = "uiautomator2";
    @Builder.Default
    private String host       = "127.0.0.1";
    @Builder.Default
    private int    port       = 4723;

    public AppiumDriverLocalService buildService () {
        final var logFile = Path.of (USER_DIR, "logs", format ("appium-{0}.log", this.driverName))
            .toFile ();
        final var builder = new AppiumServiceBuilder ();
        return builder.withIPAddress (getProperty ("host", this.host))
            .usingPort (parseInt (getProperty ("port", "" + this.port)))
            .withLogFile (logFile)
            .withArgument (BASEPATH, "/wd/hub")
            .withArgument (LOG_LEVEL, "info")
            .withArgument (USE_DRIVERS, this.driverName)
            .withArgument (SESSION_OVERRIDE)
            .withArgument (LOCAL_TIMEZONE)
            .build ();
    }
}