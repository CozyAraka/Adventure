package de.adventure.game.input;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.adventure.game.entities.statue.Statue;
import de.adventure.game.entities.statue.StatueList;

public class HitboxListener implements ContactListener {
    private Stage stage;

    private Statue statue;
    private StatueList statueList;

    public HitboxListener(Stage stage) {
        this.stage = stage;

        statueList = new StatueList();
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().isSensor()) {
            System.out.println(contact.getFixtureA().getType() + " collided with " + contact.getFixtureB().getType());
            statue = statueList.handleStatues((int) contact.getFixtureA().getUserData(), stage);
            System.out.println(contact.getFixtureA().getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().isSensor()) {
            System.out.println(contact.getFixtureA().getType() + " ended contact with " + contact.getFixtureB().getType());
            int enityID = (int) contact.getFixtureA().getUserData();

            if ((enityID <= 20 && enityID >= 0)) {
                if (statue != null) {
                    statue.endText(stage);
                    statue = null;
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
