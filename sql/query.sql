-- Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
SELECT la.ip FROM log_access la WHERE la.log_date >= STR_TO_DATE('2017-01-01.13:00:00', '%Y-%m-%d.%H:%i:%s') AND la.log_date <= STR_TO_DATE('2017-01-01.14:00:00', '%Y-%m-%d.%H:%i:%s') GROUP BY la.ip HAVING COUNT(la.ip) > 100;

-- Write MySQL query to find requests made by a given IP.
SELECT * FROM log_access WHERE ip = '192.168.228.188';