package ru.javawebinar.basejava.storage;

//public class ObjectStreamPathStorage extends AbstractPathStorage{
//    public ObjectStreamPathStorage(String directory){
//        super(directory);
//    }
//
//
//    protected void doWrite(Resume resume, OutputStream os) throws IOException {
//        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
//            oos.writeObject(resume);
//        }
//    }
//
//    protected Resume doRead(InputStream is) throws IOException {
//        Resume resume = null;
//        try (ObjectInputStream ois = new ObjectInputStream(is)) {
//            resume = (Resume) ois.readObject();
//        } catch (ClassNotFoundException e) {
//            throw new StorageException("Error read resume", null, e);
//        }
//        return resume;
//    }
//}
