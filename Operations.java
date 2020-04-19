import java.util.Scanner;

public  class Operations {
    static boolean sorted=false;//Переменная, показывающая, отсортирована ли коллекция

    public static boolean Existence(long id) {//Проверка существования объекта с заданным id в коллекции. Нужен для команды uptade_id
        boolean k = false;
        for (MusicBand band:Commands.set) {
            if (band.getId() == id) {
                k = true;
                break;
            }
        }
        return k;
    }
    public static void HistoryChange(String NewCommand){//формирование истории запросов
        for (int i=0; i<5; i++){
            Commands.history[i]=Commands.history[i+1];
        }
        Commands.history[5]=NewCommand;
    }
    public static void SortSet(){//сортировка коллекции
        if(!sorted){
            MusicBand[] bands=Commands.set.toArray(new MusicBand[Commands.set.size()]);
            boolean isSorted = false;
            MusicBand t;
            while(!isSorted) {
                isSorted = true;
                for (int i = 0; i < bands.length-1; i++) {
                    if(bands[i].compareTo(bands[i+1])>0 ){
                        isSorted = false;

                        t = bands[i];
                        bands[i] = bands[i+1];
                        bands[i+1] = t;
                    }
                }
            }
            Commands.set.clear();
            for (MusicBand band:bands) {
                Commands.set.add(band);
            }
            sorted=true;
        }
    }
    public static MusicBand GetMax(){
        Operations.SortSet();
        MusicBand[] bands=  Commands.set.toArray(new MusicBand[Commands.set.size()]);
        return bands[bands.length-1];
    }
    public static MusicBand GetMinAlbumCount(){
        MusicBand[] bands=  Commands.set.toArray(new MusicBand[Commands.set.size()]);
        MusicBand p=bands[0];
        for (int i=1; i<bands.length; i++) {
            if(bands[i].getAlbumsCount()<bands[i-1].getAlbumsCount()){
                p=bands[i];
            }
        }
        return p;
    }
    public static int GetQuantityGenres(int code){
        int Quantity=0;
        if(code<2){
            MusicBand[] bands= Commands.set.toArray(new MusicBand[Commands.set.size()]);
            for (MusicBand band : bands) {
                if (band.getGenre().ordinal() > code) {
                    Quantity++;
                }
            }
        }
        return Quantity;
    }
    public static MusicBand CreatingNewBand(String name){
        MusicBand band=new MusicBand();
        Coordinates coordinates=new Coordinates();
        band.setName(name);
        do{
            band.setId((int) (Math.random()*Integer.MAX_VALUE));
        } while(Existence(band.getId()));
        System.out.println("Введите координату x(число, большее -775, но меньшее )"+Double.MAX_VALUE);
        double x=0.0;
        boolean metka=true;
        while(metka){
            try{
                Scanner scanner=new Scanner(System.in);
                x=scanner.nextDouble();
                if(x>-775){
                    metka=false;
                }
                else {
                    System.out.println("Это число не больше -775! Попробуйте еще раз.");
                }
            }
            catch (Exception e){
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        System.out.println("Введите координату y(целое число в пределах от"+Long.MIN_VALUE+" до "+Long.MAX_VALUE+")");
        metka=true;
        long y=0;
        while(metka){
            try{
                Scanner scanner=new Scanner(System.in);
                y=scanner.nextLong();
                metka=false;
            }
            catch (Exception e){
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        coordinates.setX(x);
        coordinates.setY(y);
        band.setCoordinates(coordinates);
        System.out.println("Введите количество участников(целое число в пределах от 0 до "+Long.MAX_VALUE+")");
        metka=true;
        long NumberOfParts=0;
        while(metka){
            try {
                Scanner scanner = new Scanner(System.in);
                NumberOfParts = scanner.nextLong();
                if (NumberOfParts > 0) {
                    metka = false;
                }
                else{
                    System.out.println("Введенное число меньше, либо равно 0! Попробуйте еще раз");
                }
            } catch (Exception e) {
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        band.setNumberOfParticipants(NumberOfParts);
        System.out.println("Введите число синглов(целое число в пределах от 0 до "+Long.MAX_VALUE+")");
        long singlesCount=0L;
        metka=true;
        while(metka) {
            try {
                Scanner scanner = new Scanner(System.in);
                singlesCount = scanner.nextLong();
                if (singlesCount > 0) {
                    metka = false;
                }
                else{
                    System.out.println("Введенное число меньше, либо равно 0! Попробуйте еще раз");
                }
            } catch (Exception e) {
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        band.setSinglesCount(singlesCount);
        System.out.println("Введите число альбомов(целое число в пределах от 0 до "+Long.MAX_VALUE+")");
        long albumsCount=0;
        metka=true;
        while(metka) {
            try {
                Scanner scanner = new Scanner(System.in);
                albumsCount = scanner.nextLong();
                if (albumsCount > 0) {
                    metka = false;
                }
                else{
                    System.out.println("Введенное число меньше, либо равно 0! Попробуйте еще раз");
                }
            } catch (Exception e) {
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        band.setAlbumsCount(albumsCount);
        String genre=null;
        while (!MusicGenre.Existence(genre)){
            System.out.println("Введите жанр. Список жанров:\n" +
                    "RAP\n"+
                    "POST_ROCK\n"+
                    "BRIT_POP\n");
            Scanner scanner=new Scanner(System.in);
            genre=scanner.nextLine();
        }
        band.setGenre(MusicGenre.valueOf(genre));
        System.out.println("Введите количество песен в лучшем альбоме(целое число от 0 до "+Integer.MAX_VALUE+")");
        int length=0;
        metka=true;
        while(metka) {
            try {
                Scanner scanner = new Scanner(System.in);
                length = scanner.nextInt();
                if (length > 0) {
                    metka = false;
                }
                else{
                    System.out.println("Введенное число меньше, либо равно 0! Попробуйте еще раз");
                }
            } catch (Exception e) {
                System.out.println("Это не число, либо число не из допустимого интервала значаений, попробуйте еще раз!");
            }
        }
        System.out.println("Введите его название");
        Scanner scanner=new Scanner(System.in);
        String nameOfBestAlbum=scanner.nextLine();
        Album bestAlbum=new Album(nameOfBestAlbum, length);
        band.setBestAlbum(bestAlbum);
        return band;
    }
    public static void removing_greater(MusicBand targetBand){
        int k=Commands.set.size();
        Commands.set.removeIf(band -> band.compareTo(targetBand) > 0);
        k=k-Commands.set.size();
        System.out.println("Было удалено "+k+" объектов по вашему запросу");
    }
}
