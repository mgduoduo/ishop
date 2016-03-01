-- for tb_trade_order
CREATE INDEX o_idx_exp_no ON tb_trade_order (express_no(50));

-- for rel_idx_exp_no
CREATE INDEX rel_idx_exp_no ON tb_txn_rel (express_no(50));
CREATE INDEX rel_idx_prod_no ON tb_txn_rel (prod_no(50));

-- for tb_product
CREATE INDEX p_idx_prod_no ON tb_product (prod_no(50));