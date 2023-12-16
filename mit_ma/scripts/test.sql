use hive.jm9527_nyu_edu;
SELECT * FROM gpu0000 ORDER BY rand() LIMIT CAST (0.01 * (SELECT count(*) FROM gpu0000)) as INTEGER);
