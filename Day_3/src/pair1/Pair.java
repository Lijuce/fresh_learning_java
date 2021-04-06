package pair1;
// 2021年4月5日
// 简单泛型类设计

public class Pair<T> {  // 尖括号内的泛型参数变量
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }
    public Pair(T first, T second){
        this.first = first;
        this.second = second;
    }
    public T getFirst(){
        return first;
    }
    public T getSecond(){
        return second;
    }
    public void setFirst(T newValue){
        first = newValue;
    }
    public void setSecond(T newValue){
        second = newValue;
    }

}
