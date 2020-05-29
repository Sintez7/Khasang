package app;

import app.legacy.Product;
import java.util.Arrays;

// Класс - декоратор для легаси класса Product
// используя его как обёртку, добавляем функционал
// оставляя нетронутым легаси класс
public class MyProduct {

    private final Product localProduct;
    private int cachedHashCode;

    public MyProduct (Product product) {
        localProduct = product;
        cachedHashCode = hashCode();
    }

    public String getName() {
        return localProduct.getName();
    }
    public void setName(String name) {
        localProduct.setName(name);
        recalculateHash();
    }
    public String getCategory() {
        return localProduct.getCategory();
    }
    public void setCategory(String category) {
        localProduct.setCategory(category);
        recalculateHash();
    }
    public byte[] getInternalCode() {
        return localProduct.getInternalCode();
    }
    public void setInternalCode(byte[] internalCode) {
        localProduct.setInternalCode(internalCode);
        recalculateHash();
    }

    public Product getLocalProduct() {
        return localProduct;
    }

    public int getCachedHashCode() {
        return cachedHashCode;
    }

    private void recalculateHash() {
        cachedHashCode = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        MyProduct myProduct = null;
        if (o != null) {
            try {
                myProduct = (MyProduct) o;
            } catch (ClassCastException e) {
                return false;
            }
        }

        if (myProduct != null && this.localProduct == myProduct.localProduct)
            return true;

        if (myProduct == null || this.localProduct.getClass() != myProduct.localProduct.getClass())
            return false;

        return bruteCompare(myProduct);
    }

    private boolean bruteCompare(MyProduct myProduct) {
        return localProduct.getCategory().equals(myProduct.getCategory())
                && Arrays.equals(localProduct.getInternalCode(), myProduct.getInternalCode())
                && localProduct.getName().equals(myProduct.getName());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (localProduct.getName() == null ? 0 : localProduct.getName().hashCode());
        result = 31 * result + (localProduct.getCategory() == null ? 0 : localProduct.getCategory().hashCode());
        for (byte b : localProduct.getInternalCode()) {
            result = 31 * result + (int) b;
        }
        return result;
    }
}
