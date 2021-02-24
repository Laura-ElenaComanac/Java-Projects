package app.repository.file;

import app.domain.Raspuns;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RaspunsFile extends AbstractFileRepository<Integer, Raspuns> {
    public RaspunsFile(String fileName){
        super(fileName);
    }

    @Override
    public Raspuns extractEntity(List<String> attributes) {
        Raspuns raspuns = new Raspuns(Integer.parseInt(attributes.get(1)), attributes.get(2), Double.parseDouble(attributes.get(3)));
        raspuns.setId(Integer.parseInt(attributes.get(0)));
        return raspuns;
    }

    @Override
    protected String createEntityAsString(Raspuns entity) {
        return entity.getNrIntrebare() + ";" + entity.getNumeStudent() + ";" + entity.getPunctaj();
    }

    public void appendNumber(Integer nr){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String contents = reader.lines().reduce("", (a,b) -> a + b + '\n');
            reader.close();
            BufferedWriter writer = new BufferedWriter((new FileWriter(fileName)));
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(nr);
            printWriter.print(contents);
            printWriter.close();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }
}
