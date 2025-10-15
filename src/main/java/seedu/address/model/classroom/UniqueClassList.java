package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.classroom.exceptions.ClassNotFoundException;
import seedu.address.model.classroom.exceptions.DuplicateClassException;

/**
 * A list of tuition classes that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueClassList implements Iterable<TuitionClass> {
    private final ObservableList<TuitionClass> internalList = FXCollections.observableArrayList();
    private final ObservableList<TuitionClass> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tuition class.
     */
    public boolean contains(TuitionClass toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(c -> c.isSameClass(toCheck));
    }

    /**
     * Adds a tuition class to the list.
     */
    public void add(TuitionClass toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClassException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with the given tuition classes.
     */
    public void setClasses(List<TuitionClass> classes) {
        requireNonNull(classes);
        if (!classesAreUnique(classes)) {
            throw new DuplicateClassException();
        }
        internalList.setAll(classes);
    }

    /**
     * Removes the equivalent tuition class from the list.
     */
    public void remove(TuitionClass toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TuitionClass> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public int size() {
        return internalList.size();
    }

    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<TuitionClass> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueClassList)) {
            return false;
        }

        UniqueClassList otherUniqueClassList = (UniqueClassList) other;
        return internalList.equals(otherUniqueClassList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        if (internalList.isEmpty()) {
            return "[No classes available]";
        }
        StringBuilder sb = new StringBuilder("Classes:\n");
        internalList.forEach(c -> sb.append(" - ").append(c.getClassName()).append("\n"));
        return sb.toString().trim();
    }

    private boolean classesAreUnique(List<TuitionClass> classes) {
        for (int i = 0; i < classes.size() - 1; i++) {
            for (int j = i + 1; j < classes.size(); j++) {
                if (classes.get(i).isSameClass(classes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
