--https://leetcode.com/problems/human-traffic-of-stadium/
--Human Traffic of Stadium

SELECT DISTINCT Stad1.* from Stadium Stad1 JOIN Stadium Stad2 JOIN Stadium Stad3
ON
    (Stad1.id = Stad2.id - 1 AND Stad1.id = Stad3.id - 2) OR
    (Stad1.id = Stad2.id + 1 AND Stad1.id = Stad3.id - 1) OR
    (Stad1.id = Stad2.id + 1 AND Stad1.id = Stad3.id + 2)
WHERE
    Stad1.people>=100 AND Stad2.people>=100 AND Stad3.people>=100
ORDER BY visit_date ASC;