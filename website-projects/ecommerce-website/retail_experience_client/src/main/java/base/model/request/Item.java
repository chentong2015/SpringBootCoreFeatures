package base.model.request;

public class Item {

    private long id;
    private String category;
    private int quantity;
    private boolean hasCompleted;

    public Item() {
    }

    public Item(long id, String category, int quantity) {
        this(id, category, quantity, false);
    }

    public Item(long id, String category, int quantity, boolean hasCompleted) {
        this.id = id;
        this.category = category;
        this.quantity = quantity;
        this.hasCompleted = hasCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isHasCompleted() {
        return hasCompleted;
    }

    public void setHasCompleted(boolean hasCompleted) {
        this.hasCompleted = hasCompleted;
    }

    @Override
    public String toString() {
        return "category=" + category + ", quantity=" + quantity + ", hasCompleted=" + hasCompleted;
    }
}
