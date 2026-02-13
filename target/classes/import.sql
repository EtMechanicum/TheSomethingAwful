ALTER TABLE category ALTER COLUMN id SET DEFAULT nextval('category_seq');

INSERT INTO category(name, description, position)VALUES('Feels like someone else is here', 'Subtle presences, unexplained sensations, the feeling of not being alone. Most reports are incomplete. Some are not.', 1);
INSERT INTO category(name, description, position)VALUES('Been dreaming of this?', 'Recurring dreams, familiar places never visited, symbols that won’t go away. You’re probably not the only one.', 2);
INSERT INTO category(name, description, position)VALUES('It followed me home', 'Accounts of entities or phenomena that appear to persist after initial contact. Do not attempt to engage. Do not acknowledge.', 3);
INSERT INTO category(name, description, position)VALUES('Something is wrong with this place', 'Locations that feel incorrect. Buildings, rooms, roads, or spaces that don’t behave as expected. Maps may be unreliable.', 4);
INSERT INTO category(name, description, position)VALUES('Can`t stop thinking about it', 'Obsessions, patterns, coincidences, and thoughts that refuse to leave. Repeated posting is common here.', 5);
INSERT INTO category(name, description, position)VALUES('Static/Noise', 'Fragmented posts, corrupted memories, incomplete thoughts. Meaning not guaranteed.', 6);
INSERT INTO category(name, description, position)VALUES('MONO', ' ', 19);