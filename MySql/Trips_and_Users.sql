--https://leetcode.com/problems/trips-and-users/
--	 Trips and Users
--		 # Write your MySQL query statement below
			SELECT
				request_at AS Day,
				round(
					sum(
									 CASE WHEN -- This temporarily generates a field, which is determined by the status field. If the completion field value is 0, otherwise it is 1 or is it cancelled.
							status="completed" THEN 0
							ELSE 1 -- sum/add 1 when status is cancelled_by_driver/cancelled_by_client
									 END ) -- Use the sum function on this field to get the number of canceled
									 /count(*), 2) -- Divide it by total number of requests made by unbanned users, After that round the result to the two decimal digit
					AS "Cancellation Rate"
			FROM
					 Trips t INNER JOIN Users u ON t.Client_Id = u.Users_Id and u.Banned='No' -- limited to non-ban users
			 WHERE request_at BETWEEN "2013-10-01" AND "2013-10-03" -- Limited date
			 GROUP BY request_at -- group dates
			ORDER BY request_at;