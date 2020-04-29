import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Commands {
    static LinkedHashSet<MusicBand> set=new LinkedHashSet<MusicBand>();//сама коллекция
    static String[] history=new String[6];//массив для хранения истории команд
    JsonFile jsonFile = new JsonFile();

    public Commands() throws IOException {
        set.addAll(FileObject());
        /**
         * Добавление коллекции
         */
    }

    public static void help(){
        System.out.println("help : вывести справку по доступным командам\n"+
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, количество элементов и т.д.)\n"+
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"+
                "add name : добавить новый элемент в коллекцию c заданным названием\n"+
                "update id : обновить значение элемента коллекции, id которого равен заданному\n"+
                "remove_by_id id : удалить элемент из коллекции по его id\n"+
                "clear : очистить коллекцию\n"+
                "save : сохранить коллекцию в файл\n"+
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n"+
                "add_if_max name : добавить новый элемент в коллекцию с заданным именем, если его значение превышает значение наибольшего элемента этой коллекции\n"+
                "remove_greater name : удалить из коллекции все элементы, превышающие заданный. Ввод начинается с имени группы\n"+
                "history : вывести последние 6 команд (без их аргументов)\n"+
                "min_by_albums_count : вывести любой объект из коллекции, значение поля albumsCount которого является минимальным\n"+
                "count_greater_than_genre genre : вывести количество элементов, значение поля genre которых больше заданного. Жанры:RAP, POST_ROCK, BRIT_POP\n"+
                "print_ascending : вывести элементы коллекции в порядке возрастания\n");
        Operations.HistoryChange("help");
    }
    public static void info(){
        System.out.println("Тип коллекции: "+set.getClass());
        System.out.println("Размер коллекции: "+set.size());
        Operations.HistoryChange("info");
    }
    public static void show(){
        for (MusicBand band: set) {
            System.out.println(band.toString());
        }
        Operations.HistoryChange("show");
    }
    public static void add(String name){
        Operations.sorted=false;
        set.add(Operations.CreatingNewBand(name));
        System.out.println("Объект с названием "+name+" успешно добавлен!");
        Operations.HistoryChange("add");
    }
    public static void update_id(long id){
        String name;
        for (MusicBand band:set){
            if(band.getId()==id){
                set.remove(band);
                System.out.println("Введите имя объекта");
                Scanner scanner=new Scanner(System.in);
                if(scanner.hasNext()){
                    name=scanner.nextLine();
                    add(name);
                    break;
                }
            }
        }
        Operations.HistoryChange("update_id");
    }
    public static void remove_by_id(long id) {
        for (MusicBand band : set) {
            if (band.getId() == id) {
                set.remove(band);
                System.out.println("Элемент id="+id+" успешно удален!");
                break;
            }
        }
    }

    public static void clear(){
        set.clear();
        Operations.HistoryChange("clear");
    }
    public static void save(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите файл, в который необходимо сохарнить коллекцию");
        String file_path = sc.nextLine();
        JSONArray toFile = new JSONArray();
        for (MusicBand tempBand : set) {
            JSONObject toJson = new JSONObject();
            try {
                toJson.put("name", tempBand.getName());
                toJson.put("id", tempBand.getId());
                toJson.put("coordinate_x", tempBand.getCoordinates().getX());
                toJson.put("coordinate_y", tempBand.getCoordinates().getY());
                toJson.put("numberOfParticipants", tempBand.getNumberOfParticipants());
                toJson.put("singlesCount", tempBand.getSinglesCount());
                toJson.put("albumCount", tempBand.getAlbumsCount());
                toJson.put("genre", tempBand.getGenre().name());
                toJson.put("bestAlbum name", tempBand.getBestAlbum().getName());
                toJson.put("bestAlbum length", tempBand.getBestAlbum().getLength());
                toFile.add(toJson);
            } catch (Exception e) {
                System.out.println("Одно из полей не заполнено");
            }
        }
        try{
            File file = new File(file_path);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(toFile.toJSONString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Operations.HistoryChange("save");
    }
    public static void execute_script(String file){
        File script_file = new File(file);
        try{
            if (!script_file.exists() || !script_file.isFile()){
                System.out.println("Файла не существует");
                return;
            }
            if (!script_file.canRead()){
                System.out.println("Файл не читается");
                return;
            }
            if (script_file.length() == 0){
                System.out.println("Файл пуст");
                return;
            }
            Scanner scanner = new Scanner(script_file);
            while (scanner.hasNext()){
                try{
                    long k = 0;
                    int counter = 0;
                    String tempStr = scanner.nextLine();
                    String[] tempStrArray = tempStr.split("\\s");
                    String comparison = tempStrArray[0];
                    switch (comparison){
                        case "help":
                            Commands.help();
                            break;
                        case "info":
                            Commands.info();
                            break;
                        case "show":
                            if(!Commands.set.isEmpty()) {
                                Commands.show();
                            }
                            else{
                                System.out.println("Коллекция пуста :(");
                            }
                            break;
                        case "add":
                            if (tempStrArray[tempStrArray.length-1].equals("add")){
                                System.out.println("А что добавлять?");
                            }
                            else{
                                Commands.add(tempStrArray[tempStrArray.length-1]);
                            }
                            break;
                        case "update":
                            if (tempStrArray[tempStrArray.length-1].equals("update")) {
                                System.out.println("А что обновлять?");
                            }
                            else{
                                try {
                                    k=Long.parseLong(tempStrArray[tempStrArray.length-1]);
                                }
                                catch (Exception e){
                                    System.out.println("Попробуйте еще раз ввести команду, используя число в качесте id");
                                }
                                if(Operations.Existence(k)){
                                    Commands.update_id(k);
                                }
                                else{
                                    System.out.println("Объекта с данным id не существует");
                                }
                            }
                            break;
                        case "remove_by_id":
                            if (tempStrArray[tempStrArray.length-1].equals("update")) {
                                System.out.println("А что обновлять?");
                            }
                            else{
                                try {
                                    k=Long.parseLong(tempStrArray[tempStrArray.length-1]);
                                }
                                catch (Exception e){
                                    System.out.println("Попробуйте еще раз ввести команду, используя число в качесте id");
                                }
                                if(Operations.Existence(k)){
                                    Commands.remove_by_id(k);
                                }
                                else{
                                    System.out.println("Объекта с данным id не существует");
                                }
                            }
                            break;

                        case "clear":
                            Commands.clear();
                            break;
                        case "save" :
                            Commands.save();
                            break;
                        case "execute_script":
                            Commands.execute_script(tempStrArray[tempStrArray.length-1]);
                            break;
                        case "exit":
                            Commands.exit();
                        case "add_if_max":
                            if (tempStrArray[tempStrArray.length-1].equals("add_if_max")) {
                                System.out.println("А что добавлять?");
                            }
                            else{
                                Commands.add_if_max(tempStrArray[tempStrArray.length-1]);
                            }
                            break;
                        case "remove_greater":
                            if (tempStrArray[tempStrArray.length-1].equals("remove_greater")) {
                                System.out.println("А что удалять?");
                            }
                            else{
                                Commands.remove_greater(tempStrArray[tempStrArray.length-1]);
                            }
                            break;
                        case "history":
                            Commands.history();
                            break;
                        case "min_by_albums_count":
                            Commands.min_by_albums_count();
                            break;
                        case "count_greater_than_genre":
                            if (tempStrArray[tempStrArray.length-1].equals("count_greater_than_genre")) {
                                System.out.println("А с чем сравнивать?");
                            }
                            else{
                                if(MusicGenre.Existence(tempStrArray[tempStrArray.length-1])){
                                    System.out.println(Commands.count_greater_than_genre(MusicGenre.valueOf(tempStrArray[tempStrArray.length-1])));
                                }
                            }
                            break;
                        case "print_ascending":
                            Commands.print_ascending();
                            break;
                        default:
                            System.out.println("Введенная вами команда не соответстувет требованиям. Попробуйте еще раз");
                    }
                }catch (Exception e){
                    System.out.println("Файл не найден");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Operations.HistoryChange("execute_script");
    }
    public static void exit(){
        System.exit(0);
        Operations.HistoryChange("exit");
    }
    public static void add_if_max(String name){
        MusicBand t=Operations.GetMax();
        MusicBand p=Operations.CreatingNewBand(name);
        if(set.size()>0) {
            if (p.compareTo(t) > 0) {
                set.add(p);
                System.out.println("Элемент с названием "+name+" успешно добавлен");
            }
            else{
                System.out.println("Увы, введенный вами элемент не будет самым большим в коллекции");
            }
        }
        else{
            set.add(p);
            System.out.println("Элемент с названием "+name+" успешно добавлен");
        }
        Operations.HistoryChange("add_if_max");
    }
    public static void remove_greater(String name){
        Operations.removing_greater(Operations.CreatingNewBand(name));
        Operations.HistoryChange("remove_greater");
    }
    public static void history(){
        for (String command:history) {
            if(command!=null) {
                System.out.println(command);
            }
        }
        Operations.HistoryChange("history");
    }
    public static void min_by_albums_count(){
        System.out.println(Operations.GetMinAlbumCount());
        Operations.HistoryChange("min_by_albums_count");
    }
    public static int count_greater_than_genre(MusicGenre genre){
        Operations.HistoryChange("count_greater_than_genre");
        return Operations.GetQuantityGenres(genre.ordinal());
    }
    public static void print_ascending(){
        Operations.SortSet();
        show();
        Operations.HistoryChange("print_ascending");

    }

    /**
     * @return возвращает коллекцию полученную из файла.
     */
    public List<MusicBand> FileObject(){
        List<MusicBand> FileBand = new LinkedList<MusicBand>();
        for (int counter = 0; counter < jsonFile.getJsonCollectionSize(); counter++){
            MusicBand tempBand = new MusicBand();
            tempBand.setName(jsonFile.getName(counter));
            tempBand.setId(jsonFile.getId(counter));
            tempBand.setCoordinates(jsonFile.getCoordinates(counter));
            tempBand.setNumberOfParticipants(jsonFile.getNumberOfParticipants(counter));
            tempBand.setSinglesCount(jsonFile.getSinglesCount(counter));
            tempBand.setAlbumsCount(jsonFile.getAlbumCount(counter));
            tempBand.setGenre(jsonFile.getMusicGenre(counter));
            tempBand.setBestAlbum(jsonFile.getbestAlbum(counter));
            FileBand.add(tempBand);
        }
        return FileBand;
    }
}
