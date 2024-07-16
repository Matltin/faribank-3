package ir.ac.kntu.db;

import ir.ac.kntu.Box;

import java.util.List;
import java.util.Objects;

public class BoxDB {
    private List<Box> boxes;

    public BoxDB(List<Box> boxes) {
        this.boxes = boxes;
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void removeBox(Box box) {
        boxes.remove(box);
    }

    public Box findBox(String name) {
        for(Box box : boxes) {
            if(box.getName().equals(name)) {
                return  box;
            }
        }
        return null;
    }

    public int size() {
        return boxes.size();
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BoxDB boxDB = (BoxDB) obj;
        return Objects.equals(boxes, boxDB.boxes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boxes);
    }
}
