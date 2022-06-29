package main.java.com.utilities;

import java.util.*;



public abstract class ObjectPool<O, T, S> {
  protected static int    objectCount  = 0;
  public final     Set<O> availableSet = new HashSet<O>();
  public final     Set<O> activeSet    = new HashSet<O>();

  public abstract O createObject(T type, S argument);

  public synchronized O getObject(T t, S s) {
    O object = null;
    if (availableSet.size() > 0) {
      object = availableSet.iterator().next();
      availableSet.remove(object);
      activeSet.add(object);
    } else {
      object = createObject(t, s);
      objectCount++;
    }
    return object;
  }

  public synchronized void returnObject(O object) {
    if (object != null) {
      activeSet.remove(object);
      availableSet.add(object);
    }
  }

  // abstract  boolean validate(O o);

  @Override
  public synchronized String toString() {
    return String.format("Pool available=%d inUse=%d", availableSet.size(), activeSet.size());
  }

}
