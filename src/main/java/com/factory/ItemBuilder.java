package main.java.com.factory;




public interface ItemBuilder<T, fArgs> {
  // T build();
  
  T build(String... fArgs);
}
