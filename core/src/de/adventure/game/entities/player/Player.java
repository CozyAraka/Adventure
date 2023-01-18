package de.adventure.game.entities.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.adventure.game.entities.Entity;

public class Player extends Entity {
    private int playerSkin;

    private float x, y;

    public Player(String name, float xCord, float yCord, int playerSkin) {
        super(name, "", EntityType.HERO, EntityStatus.IDLE, xCord, yCord);

        this.playerSkin = playerSkin;
    }

    public Body createBody(World world, PolygonShape shape, float width, float height, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        shape.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }

    public Body createBody(World world, CircleShape shape, float radius, float x, float y) {
        this.x = x;
        this.y = y;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }

    public Body createBody(World world, PolygonShape shape, float width, float height, Vector2 vector) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(vector);
        bodyDef.fixedRotation = true;

        shape.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }
}
