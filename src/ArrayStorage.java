import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {

        if (!checkResumeExist(r.uuid)) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    storage[i] = r;
                    System.out.println("Резюме c uuid " + r.uuid + " успешно обновлено");
                }
            }
        }
    }

    public boolean checkResumeExist(String uuid) {
        boolean check = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                check = true;
            }
        }
        return check;
    }

    public void save(Resume r) {

        if (checkResumeExist(r.uuid)) {
            System.out.println("Резюме с таким uuid уже существует");
        } else if (size == storage.length) {
            System.out.println("Список заполен");
        } else if (r.uuid == null) {
            System.out.println("Введите uuid");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        Resume r = null;

        if (!checkResumeExist(uuid)) {
            r = new Resume();
            r.uuid = "Резюме с таким uuid не существует";
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    r = storage[i];
                }
            }
        }
        return r;
    }

    public void delete(String uuid) {
        if (!checkResumeExist(uuid)) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    break;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}