import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {

        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Резюме с таким uuid уже существует");
                return;
            }
        }

        if (size == storage.length) {
            System.out.println("Список заполен");
        } else if (r.uuid == null) {
            System.out.println("Введите uuid");
        } else {
            storage[size] = r;
            size++;
        }
    }


    Resume get(String uuid) {
        Resume r = null;

        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                r = storage[i];
            }
        }
        return r;
    }

    void delete(String uuid) {
        int cursor = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                cursor = i;
                size--;
            }
        }
        for (int i = cursor+1; i < size+1; i++) {
            storage[i - 1] = storage[i];

        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return size;
    }
}