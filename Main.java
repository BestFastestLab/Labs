import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static String JsonFilePath;

    public static void main(String[] args) throws IOException {
        JsonFilePath = args[0];
        Commands commands = new Commands();
        Scanner sc=new Scanner(System.in);
        System.out.println("Введите команду или help для получения списка доступных команд");
        long k=0;
        while(true){
            String command=sc.nextLine();//получаем команду от человека полностью
            String commandWords[]=command.split(" ");//разбиваем ее на слова
            switch (commandWords[0]){
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
                        Operations.HistoryChange("show");
                    }
                    break;
                case "add":
                    if (commandWords[commandWords.length-1].equals("add")){//случай, когда нет параметра там, где он нужен
                        System.out.println("А что добавлять?");
                    }
                    else{
                        Commands.add(commandWords[commandWords.length-1]);
                    }
                    break;
                case "update":
                    if (commandWords[commandWords.length-1].equals("update")) {
                        System.out.println("А что обновлять?");
                    }
                    else{
                        try {
                            k=Long.parseLong(commandWords[commandWords.length-1]);
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
                    if (commandWords[commandWords.length-1].equals("update")) {
                        System.out.println("А что обновлять?");
                    }
                    else{
                        try {
                            k=Long.parseLong(commandWords[commandWords.length-1]);
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
                    Commands.execute_script(commandWords[commandWords.length-1]);
                    break;
                case "exit":
                    Commands.exit();
                case "add_if_max":
                    if (commandWords[commandWords.length-1].equals("add_if_max")) {
                        System.out.println("А что добавлять?");
                    }
                    else{
                        Commands.add_if_max(commandWords[commandWords.length-1]);
                    }
                    break;
                case "remove_greater":
                    if (commandWords[commandWords.length-1].equals("remove_greater")) {
                        System.out.println("А что удалять?");
                    }
                    else{
                        Commands.remove_greater(commandWords[commandWords.length-1]);
                    }
                    break;
                case "history":
                    Commands.history();
                    break;
                case "min_by_albums_count":
                    Commands.min_by_albums_count();
                    break;
                case "count_greater_than_genre":
                    if (commandWords[commandWords.length-1].equals("count_greater_than_genre")) {
                        System.out.println("А с чем сравнивать?");
                    }
                    else{
                        if(MusicGenre.Existence(commandWords[commandWords.length-1])){
                            System.out.println(Commands.count_greater_than_genre(MusicGenre.valueOf(commandWords[commandWords.length-1])));
                        }
                    }
                    break;
                case "print_ascending":
                    Commands.print_ascending();
                    break;
                default:
                    System.out.println("Введенная вами команда не соответстувет требованиям. Попробуйте еще раз");
            }
        }

    }
}
