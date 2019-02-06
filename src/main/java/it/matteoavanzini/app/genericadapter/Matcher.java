package it.matteoavanzini.app.genericadapter;

/**
 * Created by emme on 05/03/2017.
 */

public interface Matcher<T> {
    boolean matches(T t, String search);
}
