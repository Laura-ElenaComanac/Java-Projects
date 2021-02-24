package socialnetwork.repository.file;

import socialnetwork.domain.Recipient;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.util.List;

public class RecipientFile extends AbstractFileRepository<Tuple<Long, Tuple<Long, Long>>, Recipient> {
    public RecipientFile(String fileName, Validator<Recipient> validator){
        super(fileName,validator);
    }

    @Override
    public Recipient extractEntity(List<String> attributes) {
        Long ID1 = Long.parseLong(attributes.get(0));
        Long ID2 = Long.parseLong(attributes.get(1));
        Long ID3 = Long.parseLong(attributes.get(2));

        Recipient recipient = new Recipient(ID1,ID2,ID3);
        recipient.setId(new Tuple<>(ID1,new Tuple<>(ID2,ID3)));
        return recipient;
    }

    @Override
    protected String createEntityAsString(Recipient entity) {
        return entity.getId1()+";"+entity.getId2()+";"+entity.getId3();
    }
}
