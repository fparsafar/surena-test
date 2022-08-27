package ir.surena.sample.dto;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import ir.surena.sample.util.GeneralBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceFileControl extends Control {
    private static final Logger log = LoggerFactory.getLogger(ResourceFileControl.class);
    private static ResourceFileControl instance = null;

    public ResourceFileControl() {
    }

    public static ResourceFileControl getInstance() {
        if (instance == null) {
            init();
        }

        return instance;
    }

    private static synchronized void init() {
        if (instance == null) {
            instance = new ResourceFileControl();
        }

    }

    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = this.toBundleName(baseName, locale);
        String resourceName = this.toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        String resourcePath = "";
        String userDir = System.getenv("user.dir");
        if (userDir != null) {
            resourcePath = GeneralBase.addPathSeparator(userDir);
        }

        resourcePath = resourcePath + resourceName;
        File file = new File(resourcePath);
        boolean fileExists = file.exists();
        if (reload) {
            URL url = null;
            if (fileExists) {
                url = file.toURI().toURL();
                log.debug(String.format("ResourceFileControl[%s] File[%s] [Reload]", resourceName, url));
            } else {
                url = loader.getResource(resourceName);
                log.debug(String.format("ResourceFileControl[%s] Resource[%s] [Reload]", resourceName, url));
            }

            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else if (fileExists) {
            stream = new FileInputStream(resourcePath);
            log.debug(String.format("ResourceFileControl[%s] File[%s]", resourceName, resourcePath));
        } else {
            stream = loader.getResourceAsStream(resourceName);
            log.debug(String.format("ResourceFileControl[%s] Resource[%s]", resourceName, resourceName));
        }

        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader((InputStream)stream, StandardCharsets.UTF_8));
            } finally {
                ((InputStream)stream).close();
            }
        }

        return bundle;
    }
}
