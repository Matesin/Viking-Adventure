package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Potion extends Item {

    String effectType;
    int effectValue;

    public Potion(@JsonProperty("name")String name,
                  @JsonProperty("effect_type") String effectType,
                  @JsonProperty("effect_value")int effectValue,
                  @JsonProperty("description") String description,
                  @JsonProperty("x") int worldCoordX,
                  @JsonProperty("y")int worldCoordY,
                  @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.effectType = effectType;
        this.effectValue = effectValue;
        this.description = description;
    }}
