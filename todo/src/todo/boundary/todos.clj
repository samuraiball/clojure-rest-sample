(ns todo.boundary.todos
    (:require
      [clojure.java.jdbc :as jdbc]))

(defprotocol Todos
             (get-todos [db])
             (create-todo [db params])
             (update-todo [db id params])
             (fetch-todo [db id]))

(extend-protocol Todos
                 duct.database.sql.Boundary

                 (get-todos [{:keys [spec]}]
                            (jdbc/query spec ["SELECT * FROM todos"]))

                 (fetch-todo [{:keys [spec]} id]
                             (jdbc/query spec [(format "SELECT * FROM todos WHERE id = '%s'" id)]))

                 (create-todo [{:keys [spec]} params]
                               (jdbc/insert! spec :todos {:title (:title params)}))

                 (update-todo [{:keys [spec]} id params]
                              (jdbc/update! spec :todos {:title (:title params)} ["id = ?" id])))