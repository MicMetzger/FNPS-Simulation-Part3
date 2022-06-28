package factory;

public interface ItemBuilder<T, fArgs> {
  T build(String... fArgs);
  
  // T build(String... fArgs);
}
