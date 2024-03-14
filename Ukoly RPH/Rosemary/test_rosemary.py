from rosemary import Item, update

def test_any_item_name_does_not_change():
    item = Item('Bread', days_left=3, quality=5)
    update(item)
    return item.name == 'Bread'

def test_normal_item_decreases_days_left():
    item = Item('Bread', days_left=3, quality=5)
    update(item)
    return item.days_left == 2

def test_normal_item_days_left_never_negative():
    item = Item('Bread', days_left=0, quality=5)
    update(item)
    return item.days_left == 0

def test_normal_item_decreases_quality():
    item = Item('Bread', days_left=3, quality=5)
    update(item)
    return item.quality == 4

def test_normal_item_decreases_quality_twice_as_fast_after_sell_date():
    item = Item('Bread', days_left=0, quality=5)
    update(item)
    return item.quality == 3

def test_normal_item_quality_is_never_negative():
    item = Item('Bread', days_left=0, quality=0)
    update(item)
    return item.quality == 0

def test_diamonds_quality_never_changes():
    item = Item('Diamonds', days_left=3, quality=100)
    update(item)
    return item.quality == 100

def test_diamonds_days_left_never_changes():
    item = Item('Diamonds', days_left=3, quality=100)
    update(item)
    return item.days_left == 3

def test_aged_brie_days_left_decreases():
    item = Item('Aged Brie', days_left=3, quality=5)
    update(item)
    return item.days_left == 2

def test_aged_brie_increases_quality():
    item = Item('Aged Brie', days_left=3, quality=5)
    update(item)
    return item.quality == 6

def test_aged_brie_quality_is_never_more_than_50():
    item = Item('Aged Brie', days_left=3, quality=50)
    update(item)
    return item.quality == 50

def test_tickets_days_left_decreases():
    item = Item('Tickets', days_left=15, quality=5)
    update(item)
    return item.days_left == 14

def test_tickets_value_increases_morethan10_days():
    item = Item('Tickets', days_left=15, quality=5)
    update(item)
    return item.quality == 6

def test_tickets_value_increases_6to10_days():
    item = Item('Tickets', days_left=10, quality=5)
    update(item)
    return item.quality == 7

def test_tickets_value_increases_1to5_days():
    item = Item('Tickets', days_left=5, quality=5)
    update(item)
    return item.quality == 8

def test_tickets_value_is_zero_after_deadline():
    item = Item('Tickets', days_left=1, quality=5)
    update(item)
    return item.quality == 0





