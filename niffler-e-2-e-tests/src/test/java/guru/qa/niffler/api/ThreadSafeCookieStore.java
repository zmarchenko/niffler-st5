package guru.qa.niffler.api;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public enum ThreadSafeCookieStore implements CookieStore {

    INSTANCE;

    private final ThreadLocal<CookieStore> cookieManager = ThreadLocal.withInitial(
            () -> new CookieManager(null, CookiePolicy.ACCEPT_ALL).getCookieStore()
    );

    @Override
    public void add(URI uri, HttpCookie cookie) {
        cookieManager.get().add(uri, cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return cookieManager.get().get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return cookieManager.get().getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return cookieManager.get().getURIs();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return cookieManager.get().remove(uri, cookie);
    }

    @Override
    public boolean removeAll() {
        return cookieManager.get().removeAll();
    }

    public String getCookieValue(String cookieName) {
        return getCookies().stream()
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(HttpCookie::getValue)
                .orElseThrow();
    }

}
