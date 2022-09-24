(def items {"pen" 1, "notebook" 5, "backpack" 10})
(def buyer (ref 100))
(def merchant (ref 0))


(defn Buy
  [cart]

   (def total_cost (ref 0))

  (doseq [product cart]
    (def product_name (first product))
    (def product_count (last product))
    (def product_price (get items product_name))
    (def product_cost (* product_count product_price))
    (dosync
      (ref-set total_cost (+ @total_cost product_cost)))
    (println product_name product_price "x" product_count "=" product_cost)
    )

  (if (<= @total_cost @buyer)
    (dosync
      (ref-set buyer (- @buyer @total_cost))
      (ref-set merchant (+ @merchant @total_cost))
      (println "Total cost: " @total_cost)
      (println "Success! Thank you.")
      (println "Buyer balance: " @buyer)
      (println "Merchant balance: " @merchant)
      )
    (do
      (println "Total cost: " @total_cost)
      (println "Sorry. You don't have enough money in your account.")
      (println "Buyer balance: " @buyer)
      (println "Merchant balance: " @merchant)
      )
    )
  )

(Buy {"backpack" 2, "notebook" 2})
