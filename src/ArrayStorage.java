import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {

        for (Resume resume : storage) {
            if (resume!=null&&resume.uuid.equals(r.uuid)) {
                System.out.println("Резюме с таким uuid уже существует");
                return;
            }
        }

        if (storage[storage.length - 1] != null) {
            System.out.println("Список заполен");
        } else if (r.uuid == null) {
            System.out.println("Введите uuid");

        } else if (storage[0] == null) {
            storage[0] = r;
        } else {
            for (int i = 1; i < storage.length; i++) {

                if (storage[i] == null && storage[i - 1] != null) {
                    storage[i] = r;

                    break;
                }
            }
        }
    }

    Resume get(String uuid) {
        Resume r = null;
        for (Resume resume : storage) {

            if (resume != null && resume.uuid.equals(uuid)) {
                r = resume;
            }
        }
        return r;
    }

    void delete(String uuid) {
        int cursor = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                cursor = i;
            }
        }
        for (int i = cursor + 1; i < storage.length - 1; i++) {
            storage[i - 1] = storage[i];
            storage[i] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int count = count();
        Resume[] resumes = new Resume[count];
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null) {
                resumes[i] = storage[i];
            }
        }
        return resumes;
    }

    int count() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null)
                count++;
        }
        return count;
    }


    int size() {
        return count();
    }
}