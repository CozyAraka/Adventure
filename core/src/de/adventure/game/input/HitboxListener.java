package de.adventure.game.input;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.adventure.game.entities.statue.Statue;
import de.adventure.game.entities.statue.StatueList;
import de.adventure.game.screens.MainPlayingScreen;

public class HitboxListener implements ContactListener {
    private Stage stage;

    private static Statue statue;
    private StatueList statueList;

    private static boolean touching = false;
    private static int touched = 0;

    private static Contact contact;

    //TODO Available für anderes als Statuen
    public HitboxListener(Stage stage) {
        this.stage = stage;

        statueList = new StatueList();
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().isSensor()) {
            touched = 0;
            this.contact = contact;
            touching = true;
            if(MainPlayingScreen.getPressedE()) {
                MainPlayingScreen.setPressedE();
            }
            //statue = statueList.handleStatues((int) contact.getFixtureA().getUserData(), stage);
            statue = statueList.getStatueByID((int) contact.getFixtureA().getUserData());
        }
    }

    public static Contact getContact() {
        return contact;
    }

    public static Statue getStatue() {
        return statue;
    }

    public static void setStatue(Statue newStatue) {
        statue = newStatue;
    }

    public static boolean isTouching() {
        return touching;
    }

    public static int getTouched() {
        return touched;
    }

    public static void setTouched(int newTouched) {
        touched = newTouched;
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().isSensor()) {
            touched++;
            if(!MainPlayingScreen.getPressedE()) {
                MainPlayingScreen.setPressedE();
            }
            this.contact = contact;
            touching = false;

            /*
            int enityID = (int) contact.getFixtureA().getUserData();

            if ((enityID <= 20 && enityID >= 0)) {
                if (statue != null) {
                    statue.endText(stage);
                    statue = null;
                }
            }
             */
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
