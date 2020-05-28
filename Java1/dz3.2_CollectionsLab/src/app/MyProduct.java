package app;

import app.legacy.Product;

import java.util.Arrays;
import java.util.Objects;

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
        MyProduct myProduct = (MyProduct) o;

        if (this.localProduct == myProduct.localProduct)
            return true;

        if (o == null || this.localProduct.getClass() != myProduct.localProduct.getClass())
            return false;

        if (cachedHashCode == myProduct.cachedHashCode)
            return true;

        if (bruteCompare(myProduct))
            return true;

        return false;
    }

    private boolean bruteCompare(MyProduct myProduct) {
        return localProduct.getCategory().equals(myProduct.getCategory())
                && Arrays.equals(localProduct.getInternalCode(), myProduct.getInternalCode())
                && localProduct.getName().equals(myProduct.getName());
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(localProduct.getName(), localProduct.getCategory());
        result = 31 * result + Arrays.hashCode(localProduct.getInternalCode());
        return result;
    }

    /*
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

     */
}
