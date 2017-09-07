package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.randioo.mahjong_public_server.protocol.Entity.ClientCard;
import com.randioo.mahjong_public_server.protocol.Gm.GmGameInfoResponse;
import com.randioo.majiang_collections_client.action.GameInfoAction;
import com.randioo.majiang_collections_client.component.CardDispatcher;

public class UnitTest {
    // @Test
    public void explainString2CardsTest() {
        CardDispatcher dispatcher = new CardDispatcher();
        String value = "1,\n1,2,3\n2,2\n2,2,2";
        List<List<Integer>> list = dispatcher.explainString2Cards(value);
        System.out.println(list);
    }

    @Test
    public void explainCard2String() {
        GameInfoAction action = new GameInfoAction();

        GmGameInfoResponse.Builder rb = GmGameInfoResponse.newBuilder();
        {
            ClientCard card = ClientCard.newBuilder().addAllCards(Arrays.asList(1, 1, 1)).build();
            rb.addClientCards(card);
        }
        
        {
            ClientCard card = ClientCard.newBuilder().addAllCards(Arrays.asList(12, 13, 13)).build();
            rb.addClientCards(card);
        }
        
        {
            ClientCard card = ClientCard.newBuilder().addAllCards(Arrays.asList(12, 14, 13)).build();
            rb.addClientCards(card);
        }
        
        List<ClientCard> list = rb.build().getClientCardsList();
        // for(int i = 0;)
        String s = action.explain(list);
        System.out.println(s);
    }
}
