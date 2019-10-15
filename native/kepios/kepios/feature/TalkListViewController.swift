//
//  ViewController.swift
//  kepios
//
//  Created by Romain Bsl on 30/09/2019.
//  Copyright © 2019 Romain Bsl. All rights reserved.
//

import UIKit
import kepcommon

class TalkListViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, TalkListView {
    
    private var talkListTableData: [[Talk]] = []
    
    @IBOutlet var tableview: UITableView!
    
    private var presenter: TalkListPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initPresenter()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        presenter.attachView(view: self)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        presenter.detachView()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch (segue.identifier ?? "") {
            case "ShowTalk":
                guard
                    let selectedCell = sender as? TalkListTableViewCell,
                    let selectedPath = tableview?.indexPath(for: selectedCell)
                else { return }

                let talkDetailViewController = segue.destination as! TalkDetailViewController
                let section = selectedPath.section
                let row = selectedPath.row
                if talkListTableData.count <= section || talkListTableData[section].count <= row { return }
                talkDetailViewController.talkId = talkListTableData[section][row].id
            default: break
        }
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if talkListTableData.count <= section { return nil }
        return talkListTableData[section].first?.timeslot
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return talkListTableData.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
         return talkListTableData[section].count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "customcell", for: indexPath) as! TalkListTableViewCell
        let section = indexPath.section
        let row = indexPath.row
        if talkListTableData.count <= section || talkListTableData[section].count <= row { return cell }
        cell.setup(for: talkListTableData[section][row])
        return cell
    }

    private func initPresenter() {
        presenter = InjectorCommon.init().provideTalkListPresenter()
    }
    
    func onSuccessGetTalkList(talkList: [Talk]) {
        talkListTableData = []
        
        let sections = talkList.map({$0.timeslot}).unique.sorted()
        
        for talk in talkList {
            if talkListTableData.count == 0 ||
                talkListTableData.last!.first!.timeslot != talk.timeslot  {
                talkListTableData.append([talk]);
                continue
            }
            let section = sections.index(of: talk.timeslot) ?? 0
            talkListTableData[section].append(talk)
        }

        self.tableview?.reloadData()
    }
    
    func onFailureGetTalkList(e: KotlinException) {
        print(e.message ?? "error")
    }
    
    func checkInternetConnection() -> Bool {
        return true
    }
}

class TalkListTableViewCell : UITableViewCell {
    private var talkId: String = ""
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var infoLabel: UILabel!
    
    func setup(for talk: Talk) {
        talkId = talk.id
        titleLabel?.text = talk.title
        let speakers: [Speaker] = talk.speakers
        let speakersInfo = speakers.map { (speaker) -> String in
            speaker.name
        }.joined(separator: ", ")

        if (speakersInfo == "") {
            infoLabel?.text = talk.room
        }else{
            infoLabel?.text = speakersInfo + " - " + talk.room
        }
    }
    
    func getTalkId() -> String {
        return talkId
    }
}

extension Array where Element : Equatable {
    var unique: [Element] {
        var uniqueValues: [Element] = []
        forEach { item in
            if !uniqueValues.contains(item) {
                uniqueValues += [item]
            }
        }
        return uniqueValues
    }
}
